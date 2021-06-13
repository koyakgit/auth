# For development by VSCode

# Main stage -------------------------------------------------
FROM adoptopenjdk/openjdk11:alpine
LABEL Name=KeiKotani Version=0.0.1

# ============================================================
#                                                      update
#                                                     ========
RUN apk update && \
    apk add git

# ============================================================
#                                                    Localize 
#                                                  ===========
RUN apk add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    echo "Asia/Tokyo" >  /etc/timezone && \
    apk del tzdata

# ============================================================
#                                                      nodejs
#                                                     ========
ENV ALPINE_MIRROR "http://dl-cdn.alpinelinux.org/alpine"
RUN echo "${ALPINE_MIRROR}/edge/main" >> /etc/apk/repositories
RUN apk add --no-cache nodejs-current  --repository="http://dl-cdn.alpinelinux.org/alpine/edge/community"
RUN node --version

EXPOSE 80 8080
