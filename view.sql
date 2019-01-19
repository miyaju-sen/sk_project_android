#構想画面用のVIEW
CREATE VIEW v_ideas AS (
    SELECT
        ideas.no AS idea_no, #構想No
        ideas.plot AS plot, #作品No
        ideas.idea AS idea, #起承転結番号
        ideas.note AS note, #内容
        stories.no AS story_no, #ストーリーNo
        stories.title AS title, #タイトル
        stories.story AS story, #ストーリー
        stories.deleted AS deleted #削除フラグ
    FROM ideas
        INNER JOIN stories ON ideas.no = stories.idea
    ORDER BY idea_no
);

#メモ画面用のVIEW
CREATE VIEW v_memos AS (
    SELECT
        memos.no AS memo_no, #メモNo
        memos.plot AS plot, #作品No
        memos.note AS note, #メモ内容
        memos.deleted AS memo_deleted, #メモ削除フラグ
        memo_images.no AS image_no, #メモ画像No
        memo_images.image_path AS image_path, #メモ画像
        memo_images.deleted AS image_deleted #メモ画像削除フラグ
    FROM memos
        INNER JOIN memo_images ON memos.no = memo_images.memo
    ORDER BY memo_no
);