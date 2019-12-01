##
## Makefile 
##
## The Makefile exists because I am old and
## crotchety and my fingers can't stop from
## running make commands. Obviously, everthing
## can be done running maven directly.
##

sonarcloud:
	@mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=***REMOVED*** -Dsonar.organization=mbredel-github

.PHONY: all
