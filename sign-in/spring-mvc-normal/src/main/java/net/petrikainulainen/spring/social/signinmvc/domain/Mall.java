package net.petrikainulainen.spring.social.signinmvc.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Mall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mall_id")
    private String mallId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "expire_time")
    private Long expireTime;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany
    private Set<Shop> shops;

    private static final long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    @Override
    public String toString() {
        return "Mall{" +
                "id=" + id +
                ", mallId='" + mallId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expireTime=" + expireTime +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
