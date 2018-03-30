# viki-app

The viki-app calling script is designed as a Maven Project.

## Requirements:
Java - 1.7
Maven - 3.5

## Assumptions:
1. more parameter - Assuming more parameter gives the real result i.e. whether there are more pages or not.
But I found that the more parameter doesn't give the real result i.e. it indicates false, but there are more pages that come in after that.

2. per_page parameter - Assuming we have flexibility to change this parameter.
I found that 10 per page seems inefficient as we are making too many page requests. Hence using the max limit for this per_page is 50.

3. type parameter - Assuming that the type should match only "clip" and "news_clip" as they represent clips. Code is written extendible for different
types as well. Can include or exclude whatever type is required.

## Steps to run:

Download the project.

Run the following script:

```
cd viki-app-master
./clip_count.sh
```

This will build the project, run the unit tests and then run the required program to get the count of HD Clips.


