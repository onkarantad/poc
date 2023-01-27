package denorm.test;

public class ce_id_jsonata {
    public static void main(String[] args) {
        String expr = "\"CE_ID\": $dedup($filter(contactHistory, function($v, $i, $a){\r\n" +
                "        $v.($toMillis(updateDate)) = $max($a.($toMillis(updateDate)))\r\n" +
                "        }).contactDateOfBirth ,\r\n" +
                "        $filter(contactTelephone, function($v, $i, $a){\r\n" +
                "        ($exists(discontinueDate)=false and $v.($toMillis(updateDate)) = $max($a.($toMillis(updateDate))))\r\n" +
                "        }).telephoneNumber,$filter($filter(contactEmail, function($v, $i, $a){\r\n" +
                "    $exists($v.discontinueDate)=false}),function($v, $i, $a){\r\n" +
                "    $v.($toMillis(updateDate)) = $max($a.($toMillis(updateDate)))\r\n" +
                "    }).email),\r\n" ;

        System.out.println(expr);
    }
}
