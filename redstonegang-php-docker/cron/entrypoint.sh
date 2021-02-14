#!/bin/sh
set -x
echo "Ligando Crontab!"
exec supercronic /cron/jobs
