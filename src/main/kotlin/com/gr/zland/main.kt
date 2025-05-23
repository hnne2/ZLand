//package com.gr.zland
//
//
//import org.json.JSONArray
//import org.json.JSONObject
//import java.util.*
//
//
//data class MapPoint(
//    val name: String,
//    val latitude: Double,
//    val longitude: Double
//)
//fun main() {
//    val points = listOf(
//        MapPoint("Магазин 1", 53.1278, 45.0227),
//        MapPoint("Магазин 2", 53.132, 45.039),
//        MapPoint("Магазин 3", 53.1323, 45.0228)
//    )
//
//    val url = buildMapHubLink(points)
//    println(url)
//}
//
//fun buildMapHubLink(points: List<MapPoint>): String {
//    if (points.isEmpty()) return "https://maphub.net/"
//
//    val featuresJson = points.joinToString(separator = ",") { point ->
//        """
//        {
//          "type": "Feature",
//          "geometry": {
//            "type": "Point",
//            "coordinates": [${point.longitude}, ${point.latitude}]
//          },
//          "properties": {
//            "name": "${point.name.replace("\"", "\\\"")}"
//          }
//        }
//        """.trimIndent()
//    }
//
//    val geojsonString = """
//    {
//      "type": "FeatureCollection",
//      "features": [$featuresJson]
//    }
//    """.trimIndent()
//
//    val geojsonBase64 = Base64.getUrlEncoder().encodeToString(geojsonString.toByteArray())
//
//    return "https://maphub.net/import?geojson=$geojsonBase64"
//}

