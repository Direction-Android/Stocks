package uz.azim.stocks

data class Stock(
    val id: Int,
    val img: String,
    val name: String,
    val companyName: String,
    val price: String,
    val diff: String,
    var isFavorite:Boolean = false
) {
    companion object {
        fun generateStocks(): ArrayList<Stock> {
            val list = arrayListOf<Stock>()
            list.add(
                Stock(
                    0,
                    "https://cdn6.aptoide.com/imgs/d/f/d/dfd8a1f2539c62d63ab5dbc53ee90144_icon.png",
                    "YNDX",
                    "Yandex,LLC",
                    "$4 967,6",
                    "+$55(1,15%)"
                )
            )

            list.add(
                Stock(
                    1,
                    "https://cdn6.aptoide.com/imgs/d/f/d/dfd8a1f2539c62d63ab5dbc53ee90144_icon.png",
                    "AAPL",
                    "Apple Inc.",
                    "$4 967,6",
                    "+$55(1,15%)"
                )
            )

            list.add(
                Stock(
                    2,
                    "https://cdn6.aptoide.com/imgs/d/f/d/dfd8a1f2539c62d63ab5dbc53ee90144_icon.png",
                    "GOOGL",
                    "Alphabet Class A",
                    "$4 967,6",
                    "+$55(1,15%)"
                )
            )

            list.add(
                Stock(
                    3,
                    "https://cdn6.aptoide.com/imgs/d/f/d/dfd8a1f2539c62d63ab5dbc53ee90144_icon.png",
                    "AMZN",
                    "Amazon.com",
                    "$4 967,6",
                    "-$55(1,15%)"
                )
            )
            return list
        }
    }
}