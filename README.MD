# Biorhythms Android App
## By Alex Brown

![Square Application Icon](./app/src/main/res/mipmap-xxhdpi/bio_icon_launcher.png) ![Round Application Icon](./app/src/main/res/mipmap-xxhdpi/bio_icon_launcher_round.png)

# Introduction

Personal project to implement the [biorhythms](https://en.wikipedia.org/wiki/Biorhythm_(pseudoscience)) algorithm using modern Android development practises.

# Algorithm

- Physical = sin((2 * Math.PI * daysAlive) / 23)
- Emotional = sin((2 * Math.PI * daysAlive) / 28)
- Intellectual = sin((2 * Math.PI * daysAlive) / 33)

# Charting

Built using th extremely flexible [AAInfographics](https://github.com/AAChartModel/AAChartCore-Kotlin) chart framework.

![Homescreen Example](./HomescreenExample.png) ![Daily Biorhythms Example](./DailyResultsExample.png) ![Weekly Biorhythms Example](./WeeklyResultsExample.png) ![Long Term Biorhythms Example](./LongTermResultsExample.png) 
