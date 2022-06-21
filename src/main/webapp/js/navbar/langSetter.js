const changeLang = (languageCode) => {
    document.documentElement.setAttribute("lang", languageCode);
    window.location.href = URL_add_parameter(window.location.href, "language", languageCode);
    // location.reload();
};

function URL_add_parameter(url, param, value) {
    const hash = {};
    const parser = document.createElement('a');

    parser.href = url;

    const parameters = parser.search.split(/\?|&/);

    for (let i = 0; i < parameters.length; i++) {
        if (!parameters[i])
            continue;

        const ary = parameters[i].split('=');
        hash[ary[0]] = ary[1];
    }

    hash[param] = value;

    const list = [];
    Object.keys(hash).forEach(function (key) {
        list.push(key + '=' + hash[key]);
    });

    parser.search = '?' + list.join('&');
    return parser.href;
}