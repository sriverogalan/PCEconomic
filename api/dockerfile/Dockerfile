FROM php:8.2-apache-bullseye

# Set workdir on home directory
WORKDIR /home/laravel

# Install & run the MySQL Client
RUN docker-php-ext-install mysqli pdo pdo_mysql
RUN docker-php-ext-enable mysqli pdo pdo_mysql

# Update reposotories, install Git & install composer
RUN apt update  
RUN apt install -y curl
RUN apt install -y git
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer
ENV COMPOSER_ALLOW_SUPERUSER=1
# Install Laravel

