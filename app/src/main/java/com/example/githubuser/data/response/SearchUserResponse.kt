package com.example.githubuser.data.response

data class SearchUserResponse(
    var total_count: Int = 0,
    var incomplete_results: Boolean = false,
    var items: List<Items>
) {

    data class Items(
        var id: String,
        var login: String,
        var avatarUrl: String,
        var htmlUrl: String,
        var reposUrl: String,
        var organizationsUrl: String,
        var nodeId: String
    )
}
