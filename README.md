# PTUN-Report-Tool
EasyLink SDK JavaFX Client, an app for generating report from FingerSpot machine

# Data Source
You can get the data source from dummy or machine.

## Dummy Data
```
List<Scan> scanLogs = AllScanLogs.getLocalData();
List<User> users = AllUsers.getLocalData();
```

## Machine Data
Make sure you have EasyLink SDK installed and configured (Been nightmare for me working on EasyLink SDK!)
```
List<Scan> scanLogs = AllScanLogs.getMachineData();
List<User> users = AllUsers.getMachineData();
```

## compile your self
#### first add traynotification.jar into your local maven repository
```
mvn install:install-file -Dfile=path/to/TrayNotification.jar -DgroupId=tray.notification -DartifactId=traynotification -Dversion=0.0.1 -Dpackaging=jar
```
#### then install the app
```
mvn jfx:jar
```
Your installed app exists in: projectfolder/target/jfx/app/

# OR!
Try released version, download newest version here [RELEASE](https://github.com/khyrulimam/PTUN-Report-Tool/releases), then run it. No setup needed, just make sure you have jre8 installed.

#DEFAULT CREDENTIALS
```
username: admin
password: admin
```
