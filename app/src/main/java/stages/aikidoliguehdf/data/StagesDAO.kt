package stages.aikidoliguehdf.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface StagesDao {



    /*stages*/
    @Query("SELECT * FROM Stages ORDER BY startdate ASC")
    fun viewAllStages(): Flow<List<Stages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Insert Single Course
    fun insert(stages : Stages ): Long

    @Query("DELETE FROM Stages")
    suspend fun deleteAll()

    @Query("SELECT * FROM Stages ORDER BY startdate ASC")
    fun getAll(): List<Stages>

    @Query("SELECT * FROM Stages WHERE startdate > :xmonthsold ORDER BY startdate ASC")
    fun getAllxMonths(xmonthsold: String): List<Stages>

    @Query("SELECT * FROM Stages WHERE startdate > :now ORDER BY startdate ASC")
    fun getNext(now: String): List<Stages>


    @Query("SELECT * FROM Stages WHERE idcategory LIKE :idcat ORDER BY startdate ASC")
    fun getAllbyCat(idcat: String): List<Stages>

    @Query("SELECT * FROM Stages WHERE idcategory LIKE '%' || :idcat || '%'")
    fun getByCat(idcat: String): List<Stages>

    @Query("SELECT * FROM Stages WHERE places LIKE :idplace ORDER BY startdate ASC")
    fun getAllbyPlace(idplace: String): List<Stages>

    @Query("SELECT * FROM Stages WHERE idstages = :idstages")
    fun loadAllByIds(idstages: String): List<Stages>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg stages: Stages)

    @Delete
    fun delete(stage: Stages)

    @Query("DELETE FROM Stages WHERE startdate < :xmonthsold")
    fun deleteAllxMonths(xmonthsold: String)


    //
    //Categories
    //

    @Query("SELECT * FROM Categories")
    fun viewAllCategories(): Flow<List<Categories>>


    @Insert(onConflict = OnConflictStrategy.REPLACE) // Insert single Category
    fun insertCategory(category: Categories): Long


    @Query("DELETE FROM Categories")
    suspend fun deleteAllCategories()

    @Query("SELECT * FROM Categories")
    fun getAllCategories(): List<Categories>

    @Query("SELECT * FROM Categories WHERE idcat IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Categories>

    @Query("SELECT idcat FROM Categories")
    fun listIdCat(): List<String>

    @Delete
    fun delete(categories: Categories)

    @Query("SELECT COUNT(*) FROM Stages WHERE idcategory = :idcategory")
    suspend fun countcat(idcategory: String): String

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Insert single Category
    fun insert(categories : Categories ): Long

    //
    //Places
    //

    @Query("SELECT * FROM Places")
    fun viewAllPlaces(): Flow<List<Places>>

    @Query("DELETE FROM Places")
    suspend fun deleteAllPlaces()

    @Query("SELECT * FROM Places")
    fun getAllPlaces(): List<Places>

    @Query("SELECT nameplace FROM Places WHERE idplace = :idplace")
    fun loadNamePlaceById(idplace: String): String

    @Query("SELECT address FROM Places WHERE idplace = :idplace")
    fun loadAddressPlaceById(idplace: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlaces(vararg places: Places)

    @Delete
    fun delete(places: Places)

    @Query("SELECT COUNT(*) FROM Stages WHERE places = :idplace")
    suspend fun countplaces(idplace: String): String

    //
    //ManytoMany Categories -> Stages
    //

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Insert Single CourseCategoryMap
    fun insertStagesCatMap(StagesCatMap: StagesCatMap): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllStagesCatMap(StagesCatMap: List<StagesCatMap>): List<Long>

    @Query("SELECT namecat FROM Categories INNER JOIN StagesCatMap ON  Categories.idcat=StagesCatMap.idcatmap WHERE StagesCatMap.idstagesmap = :idstages")
    fun getCatName(idstages: String): List<String>

    //
    //Favorites
    //

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFav(favorites : Favorites )

    @Query("DELETE FROM Favorites")
    suspend fun deleteAllFav()

    @Query("DELETE FROM Favorites WHERE idstagesfav = :id")
    fun deleteFavbyId(id: String)

    @Query("SELECT * FROM Favorites")
    fun getAllFavs(): List<Favorites>

    @Query("SELECT idstagesfav FROM Favorites WHERE idstagesfav = :idstage")
    fun getFavsbyStageId(idstage: String): String? /*add ? to allow null value*/

    @Delete
    fun delete(favorites: Favorites)

}