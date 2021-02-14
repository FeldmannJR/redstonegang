FROM redstonegang-php


# Add user to match user id
RUN groupadd -g 1000 www
RUN useradd -u 1000 -ms /bin/bash -g www www

USER www

COPY entrypoint.sh /entrypoint.sh
STOPSIGNAL SIGTERM
ENTRYPOINT [ "/entrypoint.sh" ]