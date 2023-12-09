package com.example.android_tflite_classifymedicalplants

import com.example.android_tflite_classifymedicalplants.Model.PlantModel

val samplePlants = listOf(
    PlantModel(
        "Bạc hà",
        "Bạc hà là một loại cây thân thảo, cao khoảng 60-80cm, có tên khoa học là Mentha Arvensis, tên tiếng Anh là mint. Cây bạc hà thân hình vuông, màu xanh hoặc tím nhạt.",
        uses = listOf(
            "Trị bệnh cảm lạnh",
            "Hỗ trợ điều trị bệnh tiểu đường",
            "Kháng viêm",
            "Giảm đau dạ dày và chứng khó tiêu",
            "Làm sạch và giảm hôi miệng",
            "Giảm căng thẳng, cải thiện sức khỏe tâm thần",
            "Hỗ trợ ngăn ngừa bệnh tim mạch",
            "Chống nhiễm trùng, giúp vết thương nhanh lành",
        ),
        arrayListOf(
            "https://suckhoedoisong.qltns.mediacdn.vn/324455921873985536/2022/4/12/photo-1649777578933-16497775803658856092.jpg",
            "https://cdn.medigoapp.com/product/bac_ha_38ba43d1fd.jpg",
            ""
        )
    ),
    PlantModel(
        "Bạch đồng nữ",
        " Bạch đồng nữ hay còn gọi là cây mò trắng, cây bấn trắng. Tên khoa học là Clerodendrum fragrans Vent, thuộc họ cỏ roi ngựa, có pháp danh khoa học là Verbenaceae.",
        uses = listOf(
            "Hạ huyết áp",
            "Lợi tiểu",
            "Giảm đau",
            "Trị mụn nhọn",
            "Trị vàng da",
            "Thanh nhiệt giải độc",
        ),
        arrayListOf(
            "https://www.vinmec.com/s3-images/size/xxlarge/20211219_151908_467418_cay-bach-dong-nu.max-1800x1800.jpg",
            "https://cdn.nhathuoclongchau.com.vn/unsafe/https://cms-prod.s3-sgn09.fptcloud.com/can_canh_loai_cay_bach_dong_nu_0a4a33288a.jpg",
            "data:image/jpeg;base64,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"
        )
    ),
    PlantModel(
        "Cam thảo đất",
        "Cam thảo đất hay cam thảo nam có tên khoa học là Seoparia Dulcis L, là loại cây thân thảo, mọc thẳng, cao 30 - 80cm, thân tròn, thuộc họ thân thảo, thân mềm, rễ hình trụ to.",
        uses = listOf(
            "Thanh nhiệt",
            "Giải độc cơ thể",
            "Mát gan",
            "Kiện tì, lợi tiêu",
            "Hạ đường huyết",
        ),
        arrayListOf(
            "", "", ""
        )
    ),

    PlantModel(
        "Cỏ mần trầu",
        "Cỏ mần trầu còn được gọi là Thanh tâm thảo, Ngưu cân thảo, Cỏ chì tía, Cỏ vườn trầu, có tên khoa học là Eleusine indica (L.) Gaertn – thuộc họ Lúa (Poaceae).",
        uses = listOf(
            "Hành huyết",
            "Bổ huyết",
            "Giải độc",
            "Lợi tiểu",
            "Mát gan",
            "Chữa chướng bụng",
            "Chữa phong thấp",
            "Chữa tiểu tiện không thông",
            "Chữa sốt rét",
            "Chữa gan ",
        ),
        arrayListOf(
            "", "", ""
        )
    ),
    PlantModel(
        "Cúc tần",
        "Cúc tần hay cây từ bi, lức, lức ấn là loài thực vật có hoa thuộc họ Cúc. Cây cúc tần chứa hợp chất β-sitosterol và stigmasterol, là những chất có tác dụng chữa bệnh đái tháo đường.",
        uses = listOf(
            "Tán phong hàn",
            "Uất hoả",
            "Tiêu độc",
            "Tiêu đờm",
            "Lợi tiểu",
            "Hoạt huyết",
            "Kháng viêm",
            "Hạ áp",
            "Bồi bổ cơ thể",
            "Kích thích hệ tiêu hoá",
        ),
        arrayListOf(
            "", "", ""
        )
    ),
    PlantModel(
        "Diệp hạ châu",
        "Cây chó đẻ răng cưa hay còn gọi là cây chó đẻ, diệp hạ châu, diệp hạ châu đắng, cây cau trời. Tên Hán Việt khác: trân châu thảo, nhật khai dạ bế, diệp hậu châu. Cây có hạt tròn xếp thành hàng dưới lá nên gọi là diệp hạ châu.",
        uses = listOf(
            "Tiêu độc",
            "Thanh can lợi mật",
            "Thông huyết",
            "Lợi tiểu",
        ),
        arrayListOf(
            "", "", ""
        )
    ),
    PlantModel(
        "Hạ khô thảo",
        "Hạ khô thảo với tên khoa học đầy đủ là Spira Prunella Vulgaris, thuộc họ Labiatae",
        uses = listOf(
            "Trị đau mắt",
            "Trị chảy nước mắt",
            "Trị lao hạch",
            "Trị bướu cổ",
            "Trị huyết áp cao",
            "Viêm thần kinh da",
            "Lở ngứa, mụn nhọn",
        ),
        arrayListOf(
            "", "", ""
        )
    ),
    PlantModel(
        "Hoắc hương", "Hoắc hương là vị thuốc quý được dùng nhiều trong Đông y", arrayListOf(
            "", "", ""
        )
    ),
    PlantModel(
        "Húng chanh",
        "Húng chanh hay tần dày lá, rau thơm lùn, rau thơm lông, rau tần là cây thuộc họ Hoa môi",
        arrayListOf(
            "", "", ""
        )
    ),
    PlantModel(
        "Hy thiêm",
        "Hy thiêm, hay còn có tên dân gian khác là cỏ đĩ, cứt lợn, hy kiểm thảo, hy tiên, niêm hồ thái, chư cao, hổ cao, chó đẻ, nụ áo rìa..., là một loài thực vật thuộc họ Cúc",
        arrayListOf(
            "", "", ""
        )
    ),
    PlantModel(
        "Kinh giới",
        "Kinh giới, kinh giới rìa hay kinh giới trồng là loài cây thảo thuộc họ Hoa môi, là một loại rau thơm và cây thuốc",
        arrayListOf(
            "", "", ""
        )
    ),
    PlantModel(
        "Sâm ngọc linh",
        "Sâm Ngọc Linh là một loài cây thuộc Họ Cuồng cuồng, còn gọi là sâm Việt Nam",
        arrayListOf(
            "", "", ""
        )
    ),
)
