FROM airhacks/glassfish
COPY ./target/DEA Spotitube.war ${DEPLOYMENT_DIR}
