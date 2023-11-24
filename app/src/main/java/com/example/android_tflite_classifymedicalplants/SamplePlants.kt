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
            "", "", ""
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
            "", "", ""
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
