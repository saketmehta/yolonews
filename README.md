# YOLONews

A shameless Hacker News clone. Because YOLO.

Built with VueJS, Java and Redis.

## Redis Schema

### Users

Every user is represented by the following fields:

A Redis hash named `user:<user id>` with the following fields:

    id -> user ID
    username -> The username
    password -> Hashed password, PBKDF2(salt|password) note: | means concatenation
    email -> Optional, used to show gravatars
    karma -> User karma, earned visiting the site and posting good stuff
    createdTime -> Registration time (unix time)
    modifiedTime -> Modified time (unix time)

Additionally for every user there is the following key:

    username.to.id:<lowercase_username> -> User ID

This is used to lookup users by name.

Frequency of user posting is limited by a key named
`user:<user_id>:submitted_recently` with TTL 15 minutes. If a user
attempts to post before that key has expired an error message notifies
the user of the amount of time until posting is permitted.


### Authentication

Users receive an authentication token after a valid pair 
of username/password is received.
The representation is a simple Redis key in the form:

    auth:<lowercase_token> -> User ID
    
### Posts

Posts are represented as an hash with key name `post:<post id>`.
The hash has the following fields:

    id -> Post id
    title -> Post title
    url -> Post url
    userId => The User ID that posted the post
    createdTime -> Post creation time. Unix time.
    modifiedTime -> Post edited time. Unix time.
    score -> Post score. See source to check how this is computed.
    rank -> Post score adjusted by age: RANK = SCORE / AGE^ALPHA
    upvotes -> Counter with number of upvotes
    downvotes -> Counter with number of downvotes
    deleted -> 1 if post is deleted.

Note: upvotes, downvotes fields are also available in other ways but we
denormalize for speed.

Also recently posted urls have a key named `url:<actual full url>` with TTL 48
hours and set to the post ID of a recently posted post having this url.

This is to make sure that the same content is not duplicated 
in a short amount of time.

Post is never deleted, but just marked as deleted adding the "deleted"
field with value 1 to the post object. However when the post is
rendered into HTML, it should be displayed as "\[deleted post\]" text.

### Votes

Every post has a sorted set with user upvotes and downvotes. The keys are named
respectively `post.upvotes:<post id>` and `post.downvotes:<post id>`.

In the sorted sets the score is the unix time of the vote creation, the element
is the user ID of the voting user.

Posting a post should automatically register an upvote from the user posting
the post.

### Saved posts

The system stores a list of upvoted posts and downvoted posts for every user using 
sorted sets named `user:<user id>:upvoted` and `user:<user id>:downvoted`, 
indexed by unix time. The value of the sorted set elements is the `<post id>`.

### Submitted posts

Like saved post every user has an associated sorted set with posts he/she posted.
The key is called `user:<user id>:posts`. Again the score is the unix time and
the element is the post id.

### Top and Latest posts

`posts.latest` is used to generate the "Latest Posts" page.
It is a sorted set where the score is the Unix time the post was posted, and the
value is the post ID.

`posts.top` is used to generate the "Top Posts" page.
It is a sorted set where the score is the "RANK" of the post, and the value is
the post ID.

## TODO

- [ ] Comments system
- [ ] Social login options
- [ ] Custom css