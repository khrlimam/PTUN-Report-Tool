package com.ptun.app.controllers;

import com.google.common.eventbus.Subscribe;
import com.j256.ormlite.dao.Dao;
import com.ptun.app.App;
import com.ptun.app.apis.GsonConverter;
import com.ptun.app.apis.enpoints.models.AllScanLogs;
import com.ptun.app.apis.enpoints.models.AllUsers;
import com.ptun.app.apis.enpoints.models.Scan;
import com.ptun.app.apis.enpoints.models.User;
import com.ptun.app.controllers.dataoperations.DataScanLogOperations;
import com.ptun.app.controllers.dataoperations.DataUserOperations;
import com.ptun.app.db.models.AppSettings;
import com.ptun.app.db.models.TimeManagement;
import com.ptun.app.enums.PEGAWAI_CHOICES;
import com.ptun.app.eventbus.EventBus;
import com.ptun.app.eventbus.events.AppSettingEvent;
import com.ptun.app.eventbus.events.ManagemenTimeEvent;
import com.ptun.app.statics.Constants;
import com.ptun.app.statics.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import lombok.Data;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.style.BorderBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import tray.notification.NotificationType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Created by Lenovo on 4/29/2017.
 */
@Data
public class ScanLogController implements Initializable {

    private DataUserOperations dataUserOperations;
    private DataScanLogOperations dataScanLogOperations;
    private Dao<com.ptun.app.db.models.User, Integer> userDao = com.ptun.app.db.models.User.getDao();
    private List<com.ptun.app.db.models.User> users = userDao.queryForAll();

    private static final String COLUMN_JABATAN_KEY = "JABATAN";
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
    public static String BASE_URL;


    @FXML
    private DatePicker dpDari, dpSampai;

    @FXML
    private ChoiceBox<String> cbPegawai;

    @FXML
    private TableColumn<Map, String> cPin, cNama, cTgl, cJamTugas,
            cJamSelesaiTugas, cScanMasuk, cScanPulang, cTerlambat,
            cLebihAwal, cAbsen, cJabatan;

    @FXML
    private TableView tblScanLog;

    public ScanLogController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventBus.getDefault().register(this);
        BASE_URL = appSettings.getURL();
        setUpTableColumns();
        setupCbPegawai();
        dpDari.setConverter(new DatePickerPattern());
        dpSampai.setConverter(new DatePickerPattern());
    }

    private void setupCbPegawai() {
        ObservableList choices = FXCollections.observableArrayList();
        choices.add("SEMUA");
        choices.addAll(Arrays.stream(PEGAWAI_CHOICES.values()).map(pegawai_choices -> pegawai_choices.name()).collect(Collectors.toList()));
        cbPegawai.setItems(choices);
        cbPegawai.getSelectionModel().selectFirst();
    }

    private void setUpTableColumns() {
        cPin.setCellValueFactory(new MapValueFactory<>(COLUMN_PIN_KEY));
        cNama.setCellValueFactory(new MapValueFactory<>(COLUMN_NAMA_KEY));
        cJabatan.setCellValueFactory(new MapValueFactory<>(COLUMN_JABATAN_KEY));
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
    private void showReport() {
        List<Scan> scanLogs = AllScanLogs.getLocalData();
        List<User> users = AllUsers.getLocalData();
        this.dataScanLogOperations = new DataScanLogOperations(scanLogs);
        this.dataUserOperations = new DataUserOperations(users);
        tblScanLog.setItems(generateDataSource(dpDari.getEditor().getText(), dpSampai.getEditor().getText(), getCbPegawai().getValue()));
    }

    private ObservableList<Map> generateDataSource(String from, String to, String jabatan) {
        ObservableList<Map> data = FXCollections.observableArrayList();
        Map<String, Map<String, List<Scan>>> grouped = this.dataScanLogOperations.groupByDateAndPIN();
        Set<String> keys = getKeysBetween(grouped.keySet(), from, to);
        try {
            TreeSet<String> sortedDate = new TreeSet<>(keys);
            sortedDate.stream().forEach(date ->
                    grouped.get(date).entrySet().stream().forEach(stringListEntry -> {
                        Map value = new HashMap();
                        Scan scanInOfCurrentUser = stringListEntry.getValue().stream().filter(scan -> scan.getIOMode() == Constants.SCAN_IN).findAny().orElse(new Scan());
                        Scan scanOutOfCurrentUser = stringListEntry.getValue().stream().filter(scan -> scan.getIOMode() == Constants.SCAN_OUT).findAny().orElse(new Scan());
                        String PIN = stringListEntry.getKey();
                        User user = getDataUserOperations().findByPIN(PIN);
                        com.ptun.app.db.models.User getUserByPinFromDB = users.stream().filter(user1 -> user1.getPIN() == Integer.parseInt(PIN)).findAny().get();
                        value.put(COLUMN_PIN_KEY, PIN);
                        value.put(COLUMN_NAMA_KEY, user.getName());
                        value.put(COLUMN_JABATAN_KEY, getUserByPinFromDB.getJabatan());
                        value.put(COLUMN_TANGGAL_KEY, date);
                        value.put(COLUMN_JAM_TUGAS_KEY, timeManagement.getOnDuty().getTime());
                        value.put(COLUMN_SELESAI_TUGAS_KEY, timeManagement.getOffDuty().getTime());
                        value.put(COLUMN_SCAN_MASUK_KEY, scanInOfCurrentUser.getScanTime());
                        value.put(COLUMN_SCAN_PULANG_KEY, scanOutOfCurrentUser.getScanTime());
                        value.put(COLUMN_TERLAMBAT_KEY, scanInOfCurrentUser.getLate(timeManagement.getLateTolerance(), timeManagement.getOnDuty().getTime()));
                        value.put(COLUMN_LEBIH_AWAL_KEY, scanOutOfCurrentUser.getEarly(timeManagement.getEarlyTolerance(), timeManagement.getOffDuty().getTime()));
                        value.put(COLUMN_ABSEN_KEY, scanInOfCurrentUser.getAbsen());

                        if (!jabatan.equalsIgnoreCase(PEGAWAI_CHOICES.HAKIM.name()) &&
                                !jabatan.equalsIgnoreCase(PEGAWAI_CHOICES.STAFF.name()))
                            data.add(value);

                        else if (jabatan.equalsIgnoreCase(getUserByPinFromDB.getJabatan()))
                            data.add(value);
                    }));
        } catch (NullPointerException e) {
            Util.showNotif("Error", "Silahkan ambil data dari mesin terlebih dahulu!", NotificationType.ERROR);
            e.printStackTrace();
        }
        return data;
    }

    private Set<String> getKeysBetween(Set<String> keys, String from, String to) {
        if (from.length() <= 0 || to.length() <= 0)
            return keys;
        return keys.stream().filter(toCompare -> isBetween(toCompare, from, to)).collect(Collectors.toSet());
    }

    private boolean isBetween(String toCompare, String from, String end) {
        //1 if greater 0 if equals -1 if less
        int a = toCompare.compareTo(from);
        int b = toCompare.compareTo(end);
        return a >= 0 && b <= 0;
    }

    @FXML
    private void downloadReport() throws FileNotFoundException, DRException {
        try {
            String from = dpDari.getEditor().getText();
            String to = dpSampai.getEditor().getText();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(App.PRIMARY_STAGE);
            OutputStream outputStream = new FileOutputStream(file);
            BufferedImage image = ImageIO.read(getClass().getResource("/logo.png"));
            BorderBuilder border = stl.border();
            border
                    .setBottomPen(stl.penThin())
                    .setLeftPen(stl.penThin())
                    .setTopPen(stl.penThin())
                    .setRightPen(stl.penThin());
            StyleBuilder style1 = stl.style()
                    .setName("style1")
                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                    .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                    .setPadding(5)
                    .setBorder(border);
            StyleBuilder columnStyle = stl.style()
                    .setName("columnStyle")
                    .setPadding(5)
                    .bold()
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            StyleBuilder columnTitleStyle = stl.style(columnStyle)
                    .setName("columnTitleStyle")
                    .setBorder(stl.pen1Point())
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            ReportTemplateBuilder template = template()
                    .templateStyles(style1, columnStyle, columnTitleStyle);
            report()
                    .setTemplate(template)
                    .setColumnStyle(stl.templateStyle("columnStyle"))
                    .setColumnTitleStyle(stl.templateStyle("columnTitleStyle"))
                    .setTemplateDesign(getClass().getClassLoader().getResource("absensikaryawan.jrxml"))
                    .columns(
                            col.column("PIN", COLUMN_PIN_KEY, type.stringType()).setStyle(style1).setWidth(45),
                            col.column("Nama", COLUMN_NAMA_KEY, type.stringType()).setStyle(style1).setWidth(162),
                            col.column("Jabatan", COLUMN_JABATAN_KEY, type.stringType()).setStyle(style1),
                            col.column("Tanggal", COLUMN_TANGGAL_KEY, type.stringType()).setStyle(style1),
                            col.column("Jam Tugas", COLUMN_JAM_TUGAS_KEY, type.stringType()).setStyle(style1),
                            col.column("Selesai Tugas", COLUMN_SELESAI_TUGAS_KEY, type.stringType()).setStyle(style1),
                            col.column("Scan Masuk", COLUMN_SCAN_MASUK_KEY, type.stringType()).setStyle(style1),
                            col.column("Scan Pulang", COLUMN_SCAN_PULANG_KEY, type.stringType()).setStyle(style1),
                            col.column("Terlambat", COLUMN_TERLAMBAT_KEY, type.stringType()).setStyle(style1),
                            col.column("Lebih Awal", COLUMN_LEBIH_AWAL_KEY, type.stringType()).setStyle(style1),
                            col.column("Absen", COLUMN_ABSEN_KEY, type.stringType()).setStyle(style1)
                    )
                    .setDataSource(generateDataSource(from, to, cbPegawai.getValue()))
                    .setParameter("logo", image)
                    .toPdf(outputStream);
            Util.showNotif("Sukses", "File absensi berhasil disimpan", NotificationType.SUCCESS);
        } catch (NullPointerException e) {
            Util.showNotif("Error", "Anda harus mendownload data terlebih dahulu", NotificationType.ERROR);
            e.printStackTrace();
        } catch (IOException e) {
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

    private class DatePickerPattern extends StringConverter<LocalDate> {
        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);

        @Override
        public String toString(LocalDate localDate) {
            if (localDate == null)
                return "";
            return dateTimeFormatter.format(localDate);
        }

        @Override
        public LocalDate fromString(String dateString) {
            if (dateString == null || dateString.trim().isEmpty()) {
                return null;
            }
            return LocalDate.parse(dateString, dateTimeFormatter);
        }
    }

}
