package spssoftware.opencare.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AbstractEntity {

	String id;

	LocalDateTime createdDate;
	LocalDateTime modifiedDate;
}
