db2servicexml
=============

A tool for programmatically creating database definitions for the Liferay service builder.

This is something to provide help when integrating existing (especially large) databases with Liferay. Currently supports the 6.1.0 DTD.

<pre>
$ mvn package
$ db2servicexml.sh --help
</pre>

Basic use case
--------------

Initial run, create a property file for your DB
<pre>
$ ./db2servicexml.sh -d jdbc:postgresql://localhost:5432/mydb -u dbuser -p dbpasswd --create-config-skeleton
</pre>
Edit the property file and set friendly names for your tables and columns
<pre>
$ ./db2servicexml.sh -d jdbc:postgresql://localhost:5432/mydb -u dbuser -p dbpasswd
</pre>
This will create a service-ext.template file that can be copy-pasted to your service.xml file.

The first step can be skipped but if you want to change display names or control service publishing, it is necessary.
