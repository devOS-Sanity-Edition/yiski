package one.devos.yiski.utils

import com.akuleshov7.ktoml.TomlIndentation
import com.akuleshov7.ktoml.TomlInputConfig
import com.akuleshov7.ktoml.TomlOutputConfig
import com.akuleshov7.ktoml.file.TomlFileReader

object TomlReader : TomlFileReader(
    inputConfig = TomlInputConfig.compliant(ignoreUnknownNames = true, allowEmptyToml = true),
    outputConfig = TomlOutputConfig.compliant(indentation = TomlIndentation.FOUR_SPACES, ignoreDefaultValues = false, explicitTables = false)
)
