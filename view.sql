#構想画面用のVIEW
CREATE VIEW v_ideas AS (
    SELECT
        ideas.no AS idea_no, #構想No
        ideas.plot AS plot, #作品No
        ideas.idea AS idea, #起承転結番号
        ideas.note AS note, #内容
        stories.no AS story_no, #ストーリーNo
        stories.story AS story, #ストーリー
        stories.deleted AS deleted #削除フラグ
    FROM ideas
        INNER JOIN stories ON ideas.no = stories.idea
    ORDER BY idea_no
);