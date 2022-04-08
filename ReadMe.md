restinfo.yaml
if no values remove the tag

all the rest call information
if cron expreassion - schedular
if no cron exp - at startup



-------------

create per second rollup job at 2am - daily
create a template mapping for reidnexing index - once
next day reindex today's rolleup index - daily
next day create a day rollup job to summarise reindexed index - once
two days after delete the per second rollup job - daily

Setting UP
==========
+ Having both these settings will allow job to run at start up time and also as a shceduled job.
    - cron: "0 2 2 1/1 * ?"
    - runonceafterinmins: 1

Before Running
==============
+ Make sure transaction template has **@timestamp** field
+ Start this app after atleast having one day of production data, restart the app if started early.

How to Run
==========
+ Make sure config folder is located in same level
+ nohup java -jar schedular-0.0.4-SNAPSHOT.jar &


Troubleshooting
===================================
+ If you see any job creation errors please restart the app next day.
- Job 1005A checks for previous day data, so restart the app on next day for proper funcion