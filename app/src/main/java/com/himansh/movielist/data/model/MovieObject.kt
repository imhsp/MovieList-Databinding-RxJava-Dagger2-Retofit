package com.himansh.movielist.data.model

data class MovieObject(var Title: String,
                       var Year: String,
                       var Rated: String,
                       var Released: String,
                       var Runtime: String,
                       var Genre: String,
                       var Director: String,
                       var Writer: String,
                       var Actors: String,
                       var Plot: String,
                       var Language: String,
                       var Country: String,
                       var Awards: String,
                       var Poster: String,
                       var Ratings: ArrayList<Rating>,
                       var Metascore: String,
                       var imdbRating: String,
                       var imdbVote: String,
                       var imdbID: String,
                       var DVD: String,
                       var BoxOffice: String,
                       var Production: String,
                       var Website: String,
                       var Response: String,
                       var Type: String)

data class Rating(val Source: String, val Value: String)