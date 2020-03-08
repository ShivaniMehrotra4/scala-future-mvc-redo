package com.knoldus.model

case class Geo(lat: String, lng: String)

case class Address(street: String, suite: String, city: String, zipcode: String, geo: Geo)

case class Company(name: String, catchPhrase: String, bs: String)

case class Users(id: String, name: String, username: String, email: String, address: Address, phone: String, website: String, company: Company)

case class Posts(userId: String, id: String, title: String, body: String)

case class Comments(postId: String, id: String, name: String, email: String, body: String)

case class UsersWithPosts(user: Users, post: List[Posts])

case class PostsWithComments(post: Posts, comments: List[Comments])