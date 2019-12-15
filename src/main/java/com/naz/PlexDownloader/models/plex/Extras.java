package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Extras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extrasId;

    @XmlAttribute(name = "size")
    private Integer size;

    @XmlAttribute(name = "Video")
    @OneToMany
    private List<Video> video;

    public Long getExtrasId() {
        return extrasId;
    }

    public void setExtrasId(Long extrasId) {
        this.extrasId = extrasId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }
}
