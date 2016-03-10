package benchmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author iisergeev ilya_sergeev@rgs.ru
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileAndStart {
    private long start;
    private byte[] file;
}
