package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository 仅仅是一个标识，表明任何继承它的均为仓库接口类
 * Repository 接口是 Spring Data 的一个核心接口，它不提供任何方法，开发者需要在自己定义的接口中声明需要的方法
 *
 * 基础的 Repository 提供了最基本的数据访问功能，其几个子接口则扩展了一些功能。它们的继承关系如下：
 * （1）CrudRepository： 继承 Repository，实现了一组 CRUD 相关的方法
 * （2）PagingAndSortingRepository： 继承 CrudRepository，实现了一组分页排序相关的方法
 * （3）JpaRepository： 继承 PagingAndSortingRepository，实现一组 JPA 规范相关的方法
 *
 * Spring Data JPA Repository的基本知识
 * https://www.cnblogs.com/mr-wuxiansheng/p/6189383.html
 *
 * 使用 Spring Data JPA 简化 JPA 开发
 * https://www.ibm.com/developerworks/cn/opensource/os-cn-spring-jpa/
 */

public interface CategoryDao extends JpaRepository<Category,Integer> {
}