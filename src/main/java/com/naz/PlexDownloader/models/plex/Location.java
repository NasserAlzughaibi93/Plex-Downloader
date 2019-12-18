package com.naz.PlexDownloader.models.plex;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Location implements Serializable {

    @Id
    @XmlAttribute
    private String id;

    @XmlAttribute
    private String path;

    @XmlAttribute
    private String country;

    @XmlAttribute
    private String code;

    @XmlAttribute
    private String city;

    @XmlAttribute
    private String coordinates;

    @XmlAttribute
    @JsonIgnore
    private String time_zone;

    @XmlAttribute
    private String postal_code;

    @XmlAttribute
    private String subdivisions;

    public String getPath ()
    {
        return path;
    }

    public void setPath (String path)
    {
        this.path = path;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getSubdivisions() {
        return subdivisions;
    }

    public void setSubdivisions(String subdivisions) {
        this.subdivisions = subdivisions;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [path = "+path+", id = "+id+"]";
    }
}