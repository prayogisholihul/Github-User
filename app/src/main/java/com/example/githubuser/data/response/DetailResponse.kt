package com.example.githubuser.data.response

data class DetailResponse(
    var login: String,
    var avatar_url: String,
    var html_url: String,
    var repos_url: String,
    var organizations_url: String,
    var node_id: String,
    var bio: String,
    var name: String
)
