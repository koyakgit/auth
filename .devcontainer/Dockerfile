# For development by VSCode

# Main stage -------------------------------------------------
FROM centos:8.1.1911
LABEL Name=FRONTEO Version=0.0.1

# ============================================================
#                                            BASIC & Localize 
#                                           ==================
RUN yum -y update && yum -y upgrade && \
    yum -y install glibc-locale-source glibc-langpack-ja && \
    localedef -i /usr/share/i18n/locales/ja_JP -f UTF-8 /usr/lib/locale/ja_JP.UTF-8
ENV TZ=Asia/Tokyo LANG=ja_JP.UTF-8 LANGUAGE=ja_JP:ja LC_ALL=ja_JP.UTF-8

# ===========================================================
#                                              Install Java
#                                              ==============
COPY ./conf/adoptopenjdk.repo /etc/yum.repos.d/adoptopenjdk.repo
RUN yum install -y lsof wget sudo git adoptopenjdk-11-hotspot
ENV JAVA_HOME=/usr/lib/jvm/adoptopenjdk-11-hotspot/

# ===========================================================
#                                            Install Node.js
#                                           =================
RUN dnf install -y gcc-c++ make && \
    curl -sL https://rpm.nodesource.com/setup_12.x | sudo -E bash - && \
    dnf install -y nodejs

# ===========================================================
#                                            Install Angular
#                                           =================
RUN npm install -g @angular/cli

# ===========================================================
#                                            Install MongoDB
#                                           =================
COPY ./conf/mongodb-org-4.2.repo /etc/yum.repos.d/.
RUN yum install -y mongodb-org-4.2.7 mongodb-org-server-4.2.7 mongodb-org-shell-4.2.7 mongodb-org-mongos-4.2.7 mongodb-org-tools-4.2.7

# mongo-express
RUN npm install -g mongo-express


EXPOSE 80 8080 4200 27017
