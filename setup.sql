GRANT ALL ON dezsys09.* TO 'dezsys09'@'localhost' IDENTIFIED BY 'dezsys09';
GRANT ALL ON dezsys09.* TO 'dezsys09'@'%' IDENTIFIED BY 'dezsys09';

DROP DATABASE IF EXISTS dezsys09;
CREATE DATABASE dezsys09;