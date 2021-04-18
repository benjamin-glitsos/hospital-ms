import { Fragment } from "react";
import { Helmet } from "react-helmet";
import ArticleLayout from "%/components/ContentLayout";

export default ({ title, description, children }) => (
    <ArticleLayout title={title} description={description} maxWidth="800px">
        {children}
    </ArticleLayout>
);
