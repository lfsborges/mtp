

# Overview

This work is part of my application to Lindacare.

Due to my available time in the 05/10 day, it was not possible to do better than this. I'm sorry.

I've done my best work in the few hours that I had to work on this project. I would appreciate if you 
consider that my current work does not meet what you were expecting that you give me the opportunity 
to do a second test.


# Deployment

This work was deployed to Heroku (integrated with the code that is on my GitHub).

My GitHub Utl is this: https://github.com/lfsborges/mtp

You can access the URL with the frontend at:

https://market-trade-processor-fb.herokuapp.com/

The end point created to receive messages (using post) is:

https://market-trade-processor-fb.herokuapp.com/api/messages

(the content should be in the format specified in the document) 

Two endpoints were created for the front end:

1. Returns a list of the last 10 processed messages (GET).
   
   https://market-trade-processor-fb.herokuapp.com/api/messages

2. Returns the frequeny of the last 100 messages grouped by currencyFrom/currencyTo (GET).

    https://market-trade-processor-fb.herokuapp.com/api/messages


# Approach taken

I think that this type of problem fits well in the Producer/Consumer Software Pattern.

The work that I've done was based on this pattern. I used the Apache Kafka, witch is a Distributed 
Streaming Platform (https://kafka.apache.org/) to produce and consume the messages. This platform 
is highly configurable, so we could achieve a higher performance by changing only the configurations.

I also used DropWizard for demonstration purposes only, because it was easier in the time that I had
to make an API and also a web page based on React.

React was my choice to the frontend, although a very simple approach has been taken, I think that the 
end result is ok.
  

# Security

Certificates where used in the configuration of Apache Kafka. 

The endpoints of the API were not protected due to time.

# Testing

I had to make choices to what prioritize, so I didn't had time to make Unit Tests. 

I only made a function in the test that is reading "fake data" generated in https://www.json-generator.com/
(about 1000 records) and is continuously sending post requests to the api.