# Meeting Planner

Meeting Planner is a Spring Boot API that automatically books the best available room based on meeting requirements.

## Features

The API provides a single endpoint to book a room based on the meeting type, number of participants, date, and time and update the schedule.

### Available Endpoint

**Book a room**
```
POST http://localhost:8080/book?meetingType=VC&participants=20&date=2025-02-05&time=1
```
#### Parameters:
| Parameter    | Type   | Description                                |
|--------------|--------|--------------------------------------------|
| meetingType  | String | Meeting type (VC, SPEC, RC, RS)            |
| participants | int    | Number of participants                     |
| date         | String | Meeting date in `YYYY-MM-DD` format        |
| time         | int    | Start time of the meeting between 0 and 12 |

#### Possible Responses:

✅ **Success**: Room assigned
```
E1003
```

❌ Error: No available room

❌ Error: Time must be between 0 and 11

❌ Error: No room fit the capacity and availability requirement
