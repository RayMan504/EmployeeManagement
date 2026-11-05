Employee Management
===================

This project is a Java web application packaged as a WAR file with Apache Ant
and deployed to Apache Tomcat 9.

Prerequisites
-------------

- Linux system with `sudo` access.
- Java Development Kit (JDK) 17. The build file compiles with Java 17.
- Apache Ant 1.10 or later.
- Apache Ivy, available to Ant so that dependencies can be resolved from
	`ivy.xml`.
- Oracle database access configured in `resources/hibernate.cfg.xml`.
- Network access for the first dependency download.

Tomcat 9 requires Java 8 or later. This application uses the `javax.servlet`
API, so use Tomcat 9 rather than Tomcat 10/11 unless the application is first
migrated to the Jakarta namespace.

Install Java and Ant on Ubuntu/Debian
-------------------------------------

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk ant ivy
java -version
ant -version
```

Install Tomcat 9 from the Ubuntu/Debian packages
-------------------------------------------------

The distribution package is the simplest option when the repository provides
Tomcat 9:

```bash
sudo apt update
apt-cache policy tomcat9
sudo apt install -y tomcat9
sudo systemctl enable --now tomcat9
systemctl status tomcat9 --no-pager
```

Open `http://localhost:8080/` to verify that Tomcat is running. The package
normally uses `/var/lib/tomcat9` as `CATALINA_BASE` and deploys applications
from `/var/lib/tomcat9/webapps`.

Install the official Tomcat 9 archive
-------------------------------------

Use this method when the operating-system repository does not provide the
required Tomcat 9 version. Replace the version with the current Tomcat 9
release from the official Apache Tomcat website.

```bash
export TOMCAT_VERSION=9.0.112
cd /tmp
wget https://dlcdn.apache.org/tomcat/tomcat-9/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz
tar -xzf apache-tomcat-${TOMCAT_VERSION}.tar.gz
sudo mv apache-tomcat-${TOMCAT_VERSION} /opt/tomcat9
sudo useradd --system --home /opt/tomcat9 --shell /usr/sbin/nologin tomcat 2>/dev/null || true
sudo chown -R tomcat:tomcat /opt/tomcat9
sudo chmod +x /opt/tomcat9/bin/*.sh
sudo -u tomcat /opt/tomcat9/bin/startup.sh
```

Verify the archive installation at `http://localhost:8080/`. Stop it with:

```bash
sudo -u tomcat /opt/tomcat9/bin/shutdown.sh
```

Build the application
---------------------

From the repository root:

```bash
ant clean package
```

The resulting WAR file is:

```text
build/dist/EmployeeManagement.war
```

Before deploying, check the database settings in
`resources/hibernate.cfg.xml`. Do not commit production credentials.

Deploy to Tomcat
----------------

For a package installation:

```bash
sudo cp build/dist/EmployeeManagement.war /var/lib/tomcat9/webapps/
sudo systemctl restart tomcat9
```

For an archive installation:

```bash
sudo cp build/dist/EmployeeManagement.war /opt/tomcat9/webapps/
sudo chown tomcat:tomcat /opt/tomcat9/webapps/EmployeeManagement.war
sudo -u tomcat /opt/tomcat9/bin/startup.sh
```

Tomcat expands the WAR automatically. Open the application at:

```text
http://localhost:8080/EmployeeManagement/
```

Troubleshooting
---------------

- Package logs: `sudo journalctl -u tomcat9 -f`
- Archive logs: `tail -f /opt/tomcat9/logs/catalina.out`
- If port 8080 is already in use, stop the other service or change the
	connector port in Tomcat's `conf/server.xml`.
- If dependency resolution fails, confirm that Ant can load Ivy and that the
	machine can reach Maven Central.
- If the application does not deploy, inspect the Tomcat log for database
	connection errors, missing dependencies, or Java version errors.
