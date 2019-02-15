package logbook.plugin.checkpowerup;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import logbook.internal.Config;
import logbook.internal.Operator;
import lombok.Data;

/**
 * 近代化改修可能な艦娘の設定
 *
 */
@Data
public class ModernizableTableConfig {

    /** 近代化改修項目 */
    private List<String> modernizedValue;

    /** レベルフィルター */
    private boolean levelEnabled;

    /** レベル */
    private String levelValue;

    /** レベル条件 */
    private Operator levelType;

    /** ロックフィルター */
    private boolean lockedEnabled;

    /** ロック済み */
    private boolean lockedValue;

    /**
     * アプリケーションのデフォルト設定ディレクトリから<code>ModernizableTableConfig</code>を取得します、
     * これは次の記述と同等です
     * <blockquote>
     *     <code>Config.getDefault().get(ModernizableTableConfig.class, ModernizableTableConfig::new)</code>
     * </blockquote>
     *
     * @return <code>ModernizableTableConfig</code>
     */
    public static ModernizableTableConfig get() {
        return Config.getDefault().get(ModernizableTableConfig.class, ModernizableTableConfig::new);
    }
}
