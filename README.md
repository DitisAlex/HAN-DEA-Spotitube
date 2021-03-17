# Spotitube

Java Assignment for the course OOSE-DEA at the [HAN University of Applied Sciences](https://www.han.nl/).
This repository contains the back-end, the front-end can be found [here](https://github.com/HANICA-DEA/spotitube).

## Hosted version

The hosted front-end application can be found here:
[Spotitube through HTTP](https://jenkins.aimsites.nl/spotitube/)
 
## API

In general the API must conform the standards of a RESTFull API. It will use HTTP methods and expects HTTP statuscodes in its response.

### Methods used 

* GET : In case of acquiring one, or multiple resources.
* POST: In case of creating a resource.
* PUT: In case of modifying  a resource.
* DELETE: In case of deleting a resource.

### Response codes

The client will expect the following respond codes to be used

* 200: OK. A response to a successful GET, PUT or DELETE.
* 201: Resource has been created. A response to a successful POST.
* 400: Bad Request. Something is wrong with the request. This could be due to
a missing query-parameter for the token.
* 401: Unauthorized. Authorization has failed. This can happen if the user tried to log in, but supplied an invalid username/password.
* 403: Forbidden. The request was valid, but you have requested a resource for which are not authorized. This will probably mean you have provided a token that is invalid.
* 404: Not found. You have requested an endpoint that is not available.

### HATEOAS

Those that are aware of the concept HATEOAS might notice that this API is not HATEOAS. That is not a problem, HATEOAS is not within the scope of this excercise.

### Endpoints
The following endpoints are expected

#### Login

```
url:    /login 
method: POST
```

It will perform a request with an object in the body of the form

```
{
  "user":     "meron", 
  "password": "MySuperSecretPassword12341"
}
```

It will expect a response containing an object of the form

```
{
  "token":  "1234-1234-1234", 
  "user":   "Meron Brouwer"
}
```

This token is then stored in LocalStorage and used for each following
request.

#### Playlists

##### Get all Playlists

To acquire a list of all playlists:
```
url:              /playlists 
method:           GET
query parameter:  token
```

It will expect a response containing the complete list of playlists:

```
{
  "playlists" :[
               {
                  "id"    : 1,
                  "name"  : "Death metal",
                  "owner" : true,
                  "tracks": []
               },
               {
                  "id"    : 2,
                  "name"  : "Pop",
                  "owner" : false,
                  "tracks": []
               }
              ],
  "length"  :123445}
```

The property `length` should be in seconds and represents the total length of all playlists. The client will convert this to hh:mm:ss.

##### Delete a Playlist

To delete a playlist:
```
url:              /playlists/:id
method:           DELETE
query parameter:  token
```
It will expect a response containing the complete and modified list of playlists:

```
{
  "playlists" :[
               {
                  "id"    : 1,
                  "name"  : "Heavy Metal",
                  "owner" : true,
                  "tracks": []
               }
              ],
  "length"  :6445}
```

##### Add a Playlist

To add a playlist:
```
url:              /playlists
method:           POST
query parameter:  token
```

The body should contain the new playlist:
```
{
  "id"    : -1,
  "name"  : "Progressive Rock",
  "owner" : false,
  "tracks": []
},
```

Note that the client will set the id to -1, and the 'owner' variable to false. The server is responsible for generating a unique id, and setting the 'owner' variable accordingly. These must be set on the response object.

It will expect a response containing the complete and modified list of playlists:

```
{
  "playlists" :[
               {
                  "id"    : 1,
                  "name"  : "Heavy Metal",
                  "owner" : true,
                  "tracks": []
               },
               {
                  "id"    : 2,
                  "name"  : "Pop",
                  "owner" : false,
                  "tracks": []
               },
               {
                 "id"    : 3,
                 "name"  : "Progressive Rock",
                 "owner" : true,
                 "tracks": []
               },
              ],
  "length"  :123445}
```
The property `length` should be in seconds. The client will convert this to hh:mm:ss.

##### Edit a Playlist

To edit the name of a playlist:
```
url:              /playlists/:id
method:           PUT
query parameter:  token
```

The body should contain the modified playlist:
```
{
  "id"    : 1,
  "name"  : "Heavy Metal",
  "owner" : true,
  "tracks": []
},
```

It will expect a response containing the complete and modified list of playlists:

```
{
  "playlists" :[
               {
                  "id"    : 1,
                  "name"  : "Heavy Metal",
                  "owner" : true,
                  "tracks": []
               },
               {
                  "id"    : 2,
                  "name"  : "Pop",
                  "owner" : false,
                  "tracks": []
               }
              ],
  "length"  :123445
}
```
The property `length` should be in seconds. The client will convert this to hh:mm:ss.
#### Tracks

##### Get all tracks 

To receive all tracks that are available

```
url:              /tracks
method:           GET
query parameter:  forPlaylist
query parameter:  token
```

The client will make this request when the user wants to add a track to a Playlist. In that case the query parameter 
`forPlaylist` is added to ensure the server only returns the Tracks that are not yet in the Playlist. The value of this query
parameter is the `id` of the Playlist.

It will expect a response containing the complete list of available tracks:

```
{
  "tracks": [
                   {
                       "id": 3,
                       "title": "Ocean and a rock",
                       "performer": "Lisa Hannigan",
                       "duration": 337,
                       "album": "Sea sew",
                       "playcount": undefined,
                       "publicationDate": undefined,
                       "description": undefined,
                       "offlineAvailable": false
                   },
                   {
                       "id": 4,
                       "title": "So Long, Marianne",
                       "performer": "Leonard Cohen",
                       "duration": 546,
                       "album": "Songs of Leonard Cohen",
                       "playcount": undefined,
                       "publicationDate": undefined,
                       "description": undefined,
                       "offlineAvailable": false
                   },
                   {
                       "id": 5,
                       "title": "One",
                       "performer": "Metallica",
                       "duration": 423,
                       "album": undefined,
                       "playcount": 37,
                       "publicationDate": "18-03-2001",
                       "description: "Long version",
                       "offlineAvailable": true
                   }
          ]
}
```

##### Get all tracks that belong to a Playlist

To receive all tracks from a given Playlist

```
url:              /playlists/:id/tracks
method:           GET
query parameter:  token
```

It will expect a response containing the complete list of tracks for the given Playlist:


```
{
  "tracks": [
            {
              "id": 1,
              "title": "Song for someone",
              "performer": "The Frames",
              "duration": 350,
              "album": "The cost",
              "playcount": undefined,
              "publicationDate": undefined,
              "description": undefined,
              "offlineAvailable": false
            },
            {
              "id": 2,
              "title": "The cost",
              "performer": "The Frames",
              "duration": 423,
              "album": undefined,
              "playcount": 37,
              "publicationDate": "19-03-2006",
              "description": "Title song from the Album The Cost",
              "offlineAvailable": true
            }
          ]
}
```
The property `duration` should be in seconds. The client will convert this to hh:mm:ss.
The property `publicationDate` should be a String representation of a Date, formatted as MM-dd-yyyy 

##### Remove a track from a Playlist

```
url:              /playlists/:id/tracks/:id
method:           DELETE
query parameter:  token
```
It will expect a response containing the complete and modified list of tracks:

```
{
  "tracks": [
            {
              "id": 1,
              "title": "Song for someone",
              "performer": "The Frames",
              "duration": 350,
              "album": "The cost",
              "playcount": undefined,
              "publicationDate": undefined,
              "description": undefined,
              "offlineAvailable": false
            }
          ]
}
```

##### Add a track to a Playlist

```
url:              /playlists/:id/tracks
method:           POST
query parameter:  token
```

The body should contain the track to be added:
```
{
  "id": 4,
  "title": "So Long, Marianne",
  "performer": "Leonard Cohen",
  "duration": 546,
  "album": "Songs of Leonard Cohen",
  "playcount": undefined,
  "publicationDate": undefined,
  "description": undefined,
  "offlineAvailable": false
}
```

Note that the relevant parts are the `id` and `offlineAvailable`. The `id` should be used by the server to lookup the
Track, before adding it to the Playlist. The offline availability should correctly be set.

It will expect a response containing the complete list of tracks for the given playlist:


```
{
  "tracks": [
            {
              "id": 1,
              "title": "Song for someone",
              "performer": "The Frames",
              "duration": 350,
              "album": "The cost",
              "playcount": undefined,
              "publicationDate": undefined,
              "description": undefined,
              "offlineAvailable": false
            },
            {
              "id": 2,
              "title": "The cost",
              "performer": "The Frames",
              "duration": 423,
              "album": undefined,
              "playcount": 37,
              "publicationDate": "19-03-2006",
              "description": "Title song from the Album The Cost",
              "offlineAvailable": true
            },
            {
              "id": 4,
              "title": "So Long, Marianne",
              "performer": "Leonard Cohen",
              "duration": 546,
              "album": "Songs of Leonard Cohen,
              "playcount": undefined,
              "publicationDate": undefined,
              "description": undefined,
              "offlineAvailable": false
            }
          ]
}
```
