package md.sancov.utils.xml

import org.xmlpull.v1.XmlSerializer

fun XmlSerializer.addNode(
    name: String,
    value: String? = null,
    setup: (XmlSerializer.() -> Unit)? = null,
): XmlSerializer {
    startTag("", name)

    if (value != null) {
        text(value)
    }

    setup?.invoke(this)

    endTag("", name)

    return this
}