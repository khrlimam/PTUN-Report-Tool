package com.ptun.app.controllers;

import com.google.common.eventbus.Subscribe;
import com.j256.ormlite.dao.Dao;
import com.ptun.app.App;
import com.ptun.app.apis.GsonConverter;
import com.ptun.app.apis.RetrofitBuilder;
import com.ptun.app.apis.enpoints.EasyLinkPoints;
import com.ptun.app.apis.enpoints.models.AllScanLogs;
import com.ptun.app.apis.enpoints.models.AllUsers;
import com.ptun.app.apis.enpoints.models.Scan;
import com.ptun.app.apis.enpoints.models.User;
import com.ptun.app.controllers.dataoperations.DataScanLogOperations;
import com.ptun.app.controllers.dataoperations.DataUserOperations;
import com.ptun.app.db.models.AppSettings;
import com.ptun.app.db.models.TimeManagement;
import com.ptun.app.eventbus.EventBus;
import com.ptun.app.eventbus.events.AppSettingEvent;
import com.ptun.app.eventbus.events.ManagemenTimeEvent;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.FileChooser;
import lombok.Data;
import net.sf.dynamicreports.report.builder.style.BorderBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalImageAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import retrofit2.Call;
import tray.notification.NotificationType;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by Lenovo on 4/29/2017.
 */
@Data
public class ScanLogController implements Initializable {

    private DataUserOperations dataUserOperations;
    private DataScanLogOperations dataScanLogOperations;

    private static final String COLUMN_PIN_KEY = "PIN";
    private static final String COLUMN_NAMA_KEY = "NAMA";
    private static final String COLUMN_TANGGAL_KEY = "TANGGAL";
    private static final String COLUMN_JAM_TUGAS_KEY = "JAM_TUGAS";
    private static final String COLUMN_SELESAI_TUGAS_KEY = "SELESAI_TUGAS";
    private static final String COLUMN_SCAN_MASUK_KEY = "SCAN_MASUK";
    private static final String COLUMN_SCAN_PULANG_KEY = "SCAN_PULANG";
    private static final String COLUMN_TERLAMBAT_KEY = "TERLAMBAT";
    private static final String COLUMN_LEBIH_AWAL_KEY = "LEBIH_AWAL";
    private static final String COLUMN_ABSEN_KEY = "ABSEN";

    private Dao<TimeManagement, Integer> timeManagementDao = TimeManagement.getDao();
    private Dao<AppSettings, Integer> appSettingDao = AppSettings.getDao();

    private TimeManagement timeManagement = timeManagementDao.queryForId(Constants.SETTING_ID);
    private AppSettings appSettings = appSettingDao.queryForId(Constants.SETTING_ID);


    @FXML
    private DatePicker dpDari, dpSampai;

    @FXML
    private TableColumn<Map, String> cPin, cNama, cTgl, cJamTugas,
            cJamSelesaiTugas, cScanMasuk, cScanPulang, cTerlambat,
            cLebihAwal, cAbsen;

    @FXML
    private TableView tblScanLog;

    public ScanLogController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.getDefault().register(this);
        setUpTableColumns();
    }

    private void setUpTableColumns() {
        cPin.setCellValueFactory(new MapValueFactory<>(COLUMN_PIN_KEY));
        cNama.setCellValueFactory(new MapValueFactory<>(COLUMN_NAMA_KEY));
        cTgl.setCellValueFactory(new MapValueFactory<>(COLUMN_TANGGAL_KEY));
        cJamTugas.setCellValueFactory(new MapValueFactory<>(COLUMN_JAM_TUGAS_KEY));
        cJamSelesaiTugas.setCellValueFactory(new MapValueFactory<>(COLUMN_SELESAI_TUGAS_KEY));
        cScanMasuk.setCellValueFactory(new MapValueFactory<>(COLUMN_SCAN_MASUK_KEY));
        cScanPulang.setCellValueFactory(new MapValueFactory<>(COLUMN_SCAN_PULANG_KEY));
        cTerlambat.setCellValueFactory(new MapValueFactory<>(COLUMN_TERLAMBAT_KEY));
        cLebihAwal.setCellValueFactory(new MapValueFactory<>(COLUMN_LEBIH_AWAL_KEY));
        cAbsen.setCellValueFactory(new MapValueFactory<>(COLUMN_ABSEN_KEY));
    }

    @FXML
    private void showReport() throws DRException, IOException {
        EasyLinkPoints easyLinkPoints = RetrofitBuilder.getInstance().create(EasyLinkPoints.class);
        Call<AllScanLogs> allScanLogs = easyLinkPoints.allScanLogs(Constants.SN);
        Call<AllUsers> allUsers = easyLinkPoints.allUsers(Constants.SN);
        List<Scan> scanLogs = allScanLogs.execute().body().getData();
        List<User> users = allUsers.execute().body().getData();
//        AllScanLogs allScanLogs = GsonConverter.getIt().fromJson(new InputStreamReader(getClass().getResourceAsStream("/dataexample/scanlog.json")), AllScanLogs.class);
//        AllUsers allUsers = GsonConverter.getIt().fromJson(new InputStreamReader(getClass().getResourceAsStream("/dataexample/users.json")), AllUsers.class);
        this.dataScanLogOperations = new DataScanLogOperations(scanLogs);
        this.dataUserOperations = new DataUserOperations(users);
        tblScanLog.setItems(generateDataSource());
    }

    private ObservableList<Map> generateDataSource() {
        ObservableList<Map> data = FXCollections.observableArrayList();
        Map<String, Map<String, List<Scan>>> grouped = this.dataScanLogOperations.groupByDateAndPIN();
        try {
            TreeSet<String> sortedDate = new TreeSet<>(grouped.keySet());
            sortedDate.stream().forEach(date -> {
                Map value = new HashMap();
                grouped.get(date).entrySet().stream().forEach(stringListEntry -> {
                    Scan scanInOfCurrentUser = stringListEntry.getValue().stream().filter(scan -> scan.getIOMode() == 1).findAny().orElse(new Scan());
                    Scan scanOutOfCurrentUser = stringListEntry.getValue().stream().filter(scan -> scan.getIOMode() == 2).findAny().orElse(new Scan());
                    User user = getDataUserOperations().findByPIN(stringListEntry.getKey());
                    value.put(COLUMN_PIN_KEY, stringListEntry.getKey());
                    value.put(COLUMN_NAMA_KEY, user.getName());
                    value.put(COLUMN_TANGGAL_KEY, date);
                    value.put(COLUMN_JAM_TUGAS_KEY, timeManagement.getOnDuty().getTime());
                    value.put(COLUMN_SELESAI_TUGAS_KEY, timeManagement.getOffDuty().getTime());
                    value.put(COLUMN_SCAN_MASUK_KEY, scanInOfCurrentUser.getScanTime());
                    value.put(COLUMN_SCAN_PULANG_KEY, scanOutOfCurrentUser.getScanTime());
                    value.put(COLUMN_TERLAMBAT_KEY, scanInOfCurrentUser.getLate(timeManagement.getLateTolerance(), timeManagement.getOnDuty().getTime()));
                    value.put(COLUMN_LEBIH_AWAL_KEY, scanOutOfCurrentUser.getEarly(timeManagement.getEarlyTolerance(), timeManagement.getOffDuty().getTime()));
                    value.put(COLUMN_ABSEN_KEY, scanInOfCurrentUser.getAbsen());
                });
                data.add(value);
            });
        } catch (NullPointerException e) {
            Util.showNotif("Error", "Silahkan ambil data dari mesin terlebih dahulu!", NotificationType.ERROR);
            e.printStackTrace();
        }
        return data;
    }

    @FXML
    private void downloadReport() throws FileNotFoundException, DRException {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(App.PRIMARY_STAGE);
            OutputStream outputStream = new FileOutputStream(file);
            BorderBuilder border = stl.border();
            border
                    .setBottomPen(stl.penDouble())
                    .setLeftPen(stl.penThin())
                    .setTopPen(stl.penThin())
                    .setRightPen(stl.penThin());
            StyleBuilder borderStyle = stl.style()
                    .setBorder(border);
            report()
                    .columns(
                            col.emptyColumn(),
                            col.column("PIN", COLUMN_PIN_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Nama", COLUMN_NAMA_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Tanggal", COLUMN_TANGGAL_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Jam Tugas", COLUMN_JAM_TUGAS_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Selesai Tugas", COLUMN_SELESAI_TUGAS_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Scan Masuk", COLUMN_SCAN_MASUK_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Scan Pulang", COLUMN_SCAN_PULANG_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Terlambat", COLUMN_TERLAMBAT_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Lebih Awal", COLUMN_LEBIH_AWAL_KEY, type.stringType()).setStyle(borderStyle),
                            col.column("Absen", COLUMN_ABSEN_KEY, type.stringType()).setStyle(borderStyle)
                    ).setColumnHeaderStyle(borderStyle)
                    .setTemplateDesign(getClass().getClassLoader().getResource("absensikaryawan.jrxml"))
                    .setDataSource(generateDataSource())
                    .toPdf(outputStream);
            Util.showNotif("Sukses", "File absensi berhasil disimpan", NotificationType.SUCCESS);
        } catch (NullPointerException e) {
            Util.showNotif("Error", "Anda harus mendownload data terlebih dahulu", NotificationType.ERROR);
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onAppSettingEvent(AppSettingEvent event) {
        this.appSettings = event.getAppSettings();
    }

    @Subscribe
    public void onManagemenTimeEvent(ManagemenTimeEvent event) {
        this.timeManagement = event.getTimeManagement();
    }

}
