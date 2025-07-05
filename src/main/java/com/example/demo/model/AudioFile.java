/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author divme
 */
@Data
public class AudioFile {
    private String uri;
    private Long creation_timestamp;
    @JsonProperty("backup_uri")
    private String backupUri;

    public String getURI() { return uri; }
    public void setURI(String value) { this.uri = value; }
}
