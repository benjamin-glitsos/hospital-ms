import { Fragment, useEffect, useCallback } from "react";
import R from "ramda";
import { useParams } from "react-router-dom";
import { useImmer } from "use-immer";
import { useForm, Controller } from "react-hook-form";
import * as Yup from "yup";
import { titleCase } from "title-case";
import axios from "axios";
import { diff } from "deep-object-diff";
import Textfield from "@atlaskit/textfield";
import { useFlags } from "@atlaskit/flag";
import "react-mde/lib/styles/css/react-mde-all.css";
import { useHistory } from "react-router-dom";
import PageContainer from "%/components/Page/PageContainer";
import OpenContainer from "%/components/Open/OpenContainer";
import RegisteredField from "%/components/RegisteredField";
import ControlledField from "%/components/ControlledField";
import MarkdownTextarea from "%/components/MarkdownTextarea";
import Open from "%/components/Open/Open";
import noNewDataToSubmitError from "%/messages/noNewDataToSubmit";
import success from "%/messages/success";
import removeAllUndefined from "%/utilities/removeAllUndefined";
import isObjectEmpty from "%/utilities/isObjectEmpty";
import toast from "%/utilities/toast";
import config from "%/config";

export default () => {
    const history = useHistory();

    const { showFlag } = useFlags();

    const { username } = useParams();

    const [nameSingular, namePlural] = config.names.users;
    const action = config.actions.EDIT;
    const title = titleCase(namePlural);
    const slug = namePlural;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        namePlural,
        description: `A ${nameSingular} in the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const openContainer = OpenContainer({
        action,
        key: username,
        nameSingular,
        namePlural,
        formFields: [
            "username",
            "email_address",
            "first_name",
            "last_name",
            "other_names",
            "additional_notes"
        ]
    });

    const pageContext = {
        ...pageContainer,
        ...openContainer
    };

    return (
        <Open context={pageContext}>
            <RegisteredField
                name="username"
                title="Username"
                Component={Textfield}
            />
            <RegisteredField
                name="email_address"
                title="Email address"
                Component={Textfield}
            />
            <RegisteredField
                name="first_name"
                title="First name"
                Component={Textfield}
            />
            <RegisteredField
                name="last_name"
                title="Last name"
                Component={Textfield}
            />
            <RegisteredField
                name="other_names"
                title="Other names"
                Component={Textfield}
            />
            <ControlledField
                name="additional_notes"
                title="Additional notes"
                Component={MarkdownTextarea}
            />
        </Open>
    );
};
