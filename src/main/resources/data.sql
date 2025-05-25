INSERT INTO article(title, content)
VALUES
    ('가가가가', '1111'),
    ('나나나나', '2222'),
    ('다다다다', '3333'),
    ('당신의 인생 영화는?', '댓글 고'),
    ('당신의 소울 푸드는?', '댓글 고고'),
    ('당신의 취미는?', '댓글 고고고');

INSERT INTO comment(article_id, nickname, body)
VALUES
    (4, 'Park', '굿 윌 헌팅'),
    (4, 'Kim', '아이 엠 샘'),
    (4, 'Choi', '쇼생크 탈출'),
    (5, 'Park', '치킨'),
    (5, 'Kim', '샤브샤브'),
    (5, 'Choi', '초밥'),
    (6, 'Park', '조깅'),
    (6, 'Kim', '유튜브 시청'),
    (6, 'Choi', '독서');
