package com.example.demo.domain.repository;

import java.util.Optional;

import com.example.demo.domain.model.UserModel;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ユーザー情報リポジトリ
 */
public interface UserRepository extends MongoRepository<UserModel, String> {
    /**
     * ログインIDを条件にユーザ情報を取得する
     * @param loginId ログインID
     * @return ユーザ情報
     */
    public Optional<UserModel> findOneByLoginId(String loginId);
}
