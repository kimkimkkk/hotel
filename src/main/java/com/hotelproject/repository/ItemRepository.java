package com.hotelproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotelproject.constant.ItemStatus;
import com.hotelproject.entity.Item;


public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{ // <해당 repository에서 사용할 Entity, Entity 클래스의 기본키 타입>
	
	List<Item> findByItemNm(String itemNm);
	List<Item> findByItemNmAndItemStatus(String itemNm, ItemStatus ItemStatus);
	List<Item> findByPriceBetween(int s, int e);
	List<Item> findByRegTimeAfter(LocalDateTime regTime);
	List<Item> findByItemStatusNotNull();
	List<Item> findByItemDetailEndingWith(String itemDetail);
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	List<Item> findByPriceLessThanOrderByPrice(int e);

	
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
	
	@Query(value = "select * from item where item_detail like %:itemDetail% order by price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	@Query("select i from Item i where i.price >= :price")
	List<Item> findByPrice(@Param("price") int price);

	@Query("select i from Item i where i.itemNm = :itemNm and i.itemStatus = :itemStatus")
	List<Item> findByItemNmAndItemStatus2(@Param("itemNm") String itemNm, @Param("itemStatus") ItemStatus itemStatus);
	
    @Modifying
    @Query(value = "delete from item where item_id = :itemId", nativeQuery = true)
    void deleteByitemIdByNative(@Param("itemId") long item);
}