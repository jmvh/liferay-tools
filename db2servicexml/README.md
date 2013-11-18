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
$ ./db2servicexml.sh -d jdbc:postgresql://localhost:5432/mydb -u dbuser -p dbpasswd \
--create-config-skeleton
</pre>
Edit the property file and set friendly names for your tables and columns
<pre>
$ emacs db-defaults.properties
</pre>
Create your service-ext.template file that can be copy-pasted to your <i>service.xml</i> file (and <i>portlet-model-hints.xml</i>).
<pre>
$ ./db2servicexml.sh -d jdbc:postgresql://localhost:5432/mydb -u dbuser -p dbpasswd \
--data-source YourSpringDataSource \
--session-factory YourSessionFactory --tx-manager YourTransactionManager \
--package-path com.your.pkg --author "Your Name"
</pre>

The parameters have, of course, to match the ones you have defined in your <i>ext-spring.xml</i> file

The first step can be skipped but if you want to change display names or control service publishing, it is necessary.

Shortcomings
------------
- If your database uses sequences, you have to configure them manually in your service.xml
- Finders can only return collections
