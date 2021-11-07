package com.example.githubuser.data.response

data class DetailResponse(
    var id: String,
    var login: String,
    var avatarUrl: String,
    var htmlUrl: String,
    var reposUrl: String,
    var organizationsUrl: String,
    var nodeId: String,
    var bio: String,
    var name: String
)
