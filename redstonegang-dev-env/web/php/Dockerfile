FROM redstonegang-php


# Add user to match user id
RUN groupadd -g 1000 www
RUN useradd -u 1000 -ms /bin/bash -g www www

USER www

WORKDIR /sites/

CMD ["php-fpm"]
