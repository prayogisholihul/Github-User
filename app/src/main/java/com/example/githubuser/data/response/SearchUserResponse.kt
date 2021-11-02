package com.example.githubuser.data.response

data class SearchUserResponse(
    var total_count: Int = 0,
    var incomplete_results: Boolean = false,
    var items: List<Items>
) {

    data class Items(
        var login: String,
        var avatar_url: String,
        var html_url: String,
        var repos_url: String,
        var organizations_url: String,
        var node_id: String
    )
}
