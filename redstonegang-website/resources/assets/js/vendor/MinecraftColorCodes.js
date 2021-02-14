var obfuscators = [];
var styleMap = {
    '§4': 'font-weight:normal;text-decoration:none;color:#be0000',
    '§c': 'font-weight:normal;text-decoration:none;color:#fe3f3f',
    '§6': 'font-weight:normal;text-decoration:none;color:#d9a334',
    '§e': 'font-weight:normal;text-decoration:none;color:#fefe3f',
    '§2': 'font-weight:normal;text-decoration:none;color:#00be00',
    '§a': 'font-weight:normal;text-decoration:none;color:#3ffe3f',
    '§b': 'font-weight:normal;text-decoration:none;color:#3ffefe',
    '§3': 'font-weight:normal;text-decoration:none;color:#00bebe',
    '§1': 'font-weight:normal;text-decoration:none;color:#0000be',
    '§9': 'font-weight:normal;text-decoration:none;color:#3f3ffe',
    '§d': 'font-weight:normal;text-decoration:none;color:#fe3ffe',
    '§5': 'font-weight:normal;text-decoration:none;color:#be00be',
    '§f': 'font-weight:normal;text-decoration:none;color:#ffffff',
    '§7': 'font-weight:normal;text-decoration:none;color:#bebebe',
    '§8': 'font-weight:normal;text-decoration:none;color:#3f3f3f',
    '§0': 'font-weight:normal;text-decoration:none;color:#000000',
    '§l': 'font-weight:bold',
    '§n': 'text-decoration:underline;text-decoration-skip:spaces',
    '§o': 'font-style:italic',
    '§m': 'text-decoration:line-through;text-decoration-skip:spaces',
};

function applyCode(string, codes) {
    var len = codes.length;
    var elem = {};
    for (var i = 0; i < len; i++) {
        elem.style += styleMap[codes[i]] + ';';
    }
    elem.content = string;
    return elem;
}

function minecraftColors(string) {
    var codes = string.match(/§.{1}/g) || [],
        indexes = [],
        apply = [],
        tmpStr,
        indexDelta,
        noCode,
        final = [],
        len = codes.length,
        string = string.replace(/\n|\\n/g, '<br>');

    for (var i = 0; i < len; i++) {
        indexes.push(string.indexOf(codes[i]));
        string = string.replace(codes[i], '\x00\x00');
    }
    if (indexes[0] !== 0) {
        final.push(applyCode(string.substring(0, indexes[0]), []));
    }
    for (var i = 0; i < len; i++) {
        indexDelta = indexes[i + 1] - indexes[i];
        if (indexDelta === 2) {
            while (indexDelta === 2) {
                apply.push(codes[i]);
                i++;
                indexDelta = indexes[i + 1] - indexes[i];
            }
            apply.push(codes[i]);
        } else {
            apply.push(codes[i]);
        }
        if (apply.lastIndexOf('§r') > -1) {
            apply = apply.slice(apply.lastIndexOf('§r') + 1);
        }
        tmpStr = string.substring(indexes[i], indexes[i + 1]);
        final.push(applyCode(tmpStr, apply));
    }
    return final;
}

function clearObfuscators() {
    var i = obfuscators.length;
    for (; i--;) {
        clearInterval(obfuscators[i]);
    }
    obfuscators = [];
}

/////////////////////////////////////////////////
function cutString(str, cutStart, cutEnd) {
    return str.substr(0, cutStart) + str.substr(cutEnd + 1);
}

export default minecraftColors;