package top.liubaiblog.poh.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 定位信息表
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@TableName("poh_location")
@ApiModel(value = "Location对象", description = "定位信息表")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId
    private Long lid;

    @ApiModelProperty("所属设备id")
    private Long equipmentId;

    @ApiModelProperty("定位时间")
    private LocalDateTime locateTime;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("经度半球(E东，W西)")
    private String longitudeHemisphere;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("纬度半球(N北，S南)")
    private String latitudeHemisphere;

    @ApiModelProperty("可视卫星总数")
    private Integer satelliteNum;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改者")
    private Long lastUpdateBy;

    @ApiModelProperty("最后修改时间")
    private LocalDateTime lastUpdateTime;

    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }
    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }
    public LocalDateTime getLocateTime() {
        return locateTime;
    }

    public void setLocateTime(LocalDateTime locateTime) {
        this.locateTime = locateTime;
    }
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getLongitudeHemisphere() {
        return longitudeHemisphere;
    }

    public void setLongitudeHemisphere(String longitudeHemisphere) {
        this.longitudeHemisphere = longitudeHemisphere;
    }
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public String getLatitudeHemisphere() {
        return latitudeHemisphere;
    }

    public void setLatitudeHemisphere(String latitudeHemisphere) {
        this.latitudeHemisphere = latitudeHemisphere;
    }
    public Integer getSatelliteNum() {
        return satelliteNum;
    }

    public void setSatelliteNum(Integer satelliteNum) {
        this.satelliteNum = satelliteNum;
    }
    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "Location{" +
            "lid=" + lid +
            ", equipmentId=" + equipmentId +
            ", locateTime=" + locateTime +
            ", longitude=" + longitude +
            ", longitudeHemisphere=" + longitudeHemisphere +
            ", latitude=" + latitude +
            ", latitudeHemisphere=" + latitudeHemisphere +
            ", satelliteNum=" + satelliteNum +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
            ", lastUpdateBy=" + lastUpdateBy +
            ", lastUpdateTime=" + lastUpdateTime +
        "}";
    }
}
