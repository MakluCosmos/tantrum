Tantrum

Assignment for Tatum.


I was not able to finish the assignment on time due to lack of time. I started with nestjs for the 
backend, and used reactjs and nextjs for the frontend. However, I changed to Spring Java backend,
as it allowed me a faster development. Too many new technologies bring too much complexity and cost
too much time as several issues with reactjs and nextjs cost some time already.

I finished most endpoints as I wanted them, but the s3 resource endpoint throws an exception which I was not able to fix in time.
Unfortunately using AWS was also not helpful as I didn't know all the services well and which one do not produce costs.
The frontend but I was not able to connect it to the backend yet.

I see this as a failed assignment, but still wanted to share the results.

HOW TO

BACKEND
You can run the backend after entering ACCESS-KEY and SECRET-KEY in the application properties. Run mvn clean install and then you can run mvn spring-boot:run.

Access the enpoint e.g. http://localhost:8080/api/resources/metadata/ec2 in Chrome to get the results.


FRONTEND
You can run the frontend with just npm run dev for local running and then access on http://localhost:3000/
Not much to see as it is all just mocked. Nothing too functional. Just some routing, disbaled second dropdown if nothing is selected in the first. 
Fetch list is enabled whenever, so selection doesn't matter. And just a mocked table. Styling is also just basic material with no further proper css from my side.
