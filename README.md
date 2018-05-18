# JSPとサーブレットとMySQLで匿名掲示板

DB名はdeaiDB

以下のテーブルを作成

```sql:postlist
CREATE TABLE `postlist` (
  `postid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `appeal` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`postid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```

```sql:replylist
CREATE TABLE `replylist` (
  `replyid` int(11) NOT NULL AUTO_INCREMENT,
  `getpostid` int(11) DEFAULT NULL,
  `appeal` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`replyid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```
