package com.example.norwayapplicationxml.domain.model

import java.io.Serializable


class New : Serializable{
    var title: String
    var by: String
    var votes: String
    var readDuration: String
    var date: String
    var likes:Int =0
    var image: String
    var content: String=""
    var articleContent:List<String> = emptyList()
    var link: String=""
    var footerContent:List<String> = emptyList()
    var publisher: WebPair? = null
    var youtubeLink: WebPair? = null
    var cats: List<WebPair>
    var tags: List<WebPair>? = null

    constructor(
        title: String,
        by: String,
        votes: String,
        readDuration: String,
        date: String,
        image: String,
        content: String,
        cats: List<WebPair>,
        link: String
    ) {
        this.title = title
        this.by = by
        this.votes = votes
        this.readDuration = readDuration
        this.date = date
        this.image = image
        this.content = content
        this.link = link
        this.cats = cats
    }

    constructor(
        title: String,
        content:String,
        date: String,
        imageUrl: String,
        link: String
    ){
        this.title = title
        this.content = content
        this.date =date
        this.image = imageUrl
        this.link = link
        this.by = ""
        this.cats = emptyList()
        this.votes = ""
        this.readDuration = ""
    }



    constructor(
        title: String,
        byline: String,
        votes: String,
        readDuration: String,
        postedOn: String,
        likes: Int,
        imageUrl: String,
        articleContent: List<String>,
        footerContent: List<String>,
        publisher: WebPair,
        youtubeLink: WebPair,
        cats: List<WebPair>,
        tags: List<WebPair>
    ){
        this.title = title
        this.by = byline
        this.votes = votes
        this.readDuration = readDuration
        this.date = postedOn
        this.likes = likes
        this.image = imageUrl
        this.articleContent = articleContent
        this.footerContent = footerContent
        this.publisher = publisher
        this.youtubeLink = youtubeLink
        this.cats = cats
        this.tags = tags
    }



    override fun toString(): String {
        return "New{" +
                "title='" + title + '\'' +
                ", by='" + by + '\'' +
                ", votes='" + votes + '\'' +
                ", readDuration='" + readDuration + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", link='" + link + '\'' +
                ", footerContent='" + footerContent + '\'' +
                ", publisher=" + publisher +
                ", youtubeLink=" + youtubeLink +
                ", cats=" + cats +
                ", tags=" + tags +
                '}'
    }
}

